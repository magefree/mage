package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Susucr
 */
public final class ShadowfaxLordOfHorses extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HORSE, "Horses");

    public ShadowfaxLordOfHorses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Horses you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter)));

        // Whenever Shadowfax, Lord of Horses attacks, you may put a creature card
        // with lesser power from your hand onto the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new ShadowfaxLordOfHorsesEffect()));
    }

    private ShadowfaxLordOfHorses(final ShadowfaxLordOfHorses card) {
        super(card);
    }

    @Override
    public ShadowfaxLordOfHorses copy() {
        return new ShadowfaxLordOfHorses(this);
    }
}

class ShadowfaxLordOfHorsesEffect extends OneShotEffect {

    ShadowfaxLordOfHorsesEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may put a creature card with lesser power " +
            "from your hand onto the battlefield tapped and attacking.";
    }

    private ShadowfaxLordOfHorsesEffect(final ShadowfaxLordOfHorsesEffect effect) {
        super(effect);
    }

    @Override
    public ShadowfaxLordOfHorsesEffect copy() {
        return new ShadowfaxLordOfHorsesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Permanent shadowfax = source.getSourcePermanentOrLKI(game);
        if (shadowfax == null) {
            return false;
        }

        FilterCreatureCard filter = new FilterCreatureCard("a creature card with lesser power");
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, shadowfax.getPower().getValue()));
        TargetCardInHand target = new TargetCardInHand(0,1,filter);
        target.setNotTarget(true);

        if (!player.choose(outcome, player.getHand(), target, source, game)) {
            return false;
        }

        Card card = game.getCard(target.getFirstTarget());
        if(card == null) {
            return false;
        }

        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            game.getCombat().addAttackingCreature(permanent.getId(), game);
        }

        return true;
    }
}