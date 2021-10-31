package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OliviaCrimsonBride extends CardImpl {

    public OliviaCrimsonBride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Olivia, Crimson Bride attacks, return target creature card from your graveyard to the battlefield tapped and attacking. It gains "When you don't control a legendary Vampire, exile this creature."
        Ability ability = new AttacksTriggeredAbility(new OliviaCrimsonBrideEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private OliviaCrimsonBride(final OliviaCrimsonBride card) {
        super(card);
    }

    @Override
    public OliviaCrimsonBride copy() {
        return new OliviaCrimsonBride(this);
    }
}

class OliviaCrimsonBrideEffect extends OneShotEffect {

    OliviaCrimsonBrideEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield tapped and attacking. " +
                "It gains \"When you don't control a legendary Vampire, exile this creature.\"";
    }

    private OliviaCrimsonBrideEffect(final OliviaCrimsonBrideEffect effect) {
        super(effect);
    }

    @Override
    public OliviaCrimsonBrideEffect copy() {
        return new OliviaCrimsonBrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        game.addEffect(new GainAbilityTargetEffect(
                new OliviaCrimsonBrideAbility(), Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

class OliviaCrimsonBrideAbility extends StateTriggeredAbility {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public OliviaCrimsonBrideAbility() {
        super(Zone.BATTLEFIELD, new ExileSourceEffect());
    }

    public OliviaCrimsonBrideAbility(final OliviaCrimsonBrideAbility ability) {
        super(ability);
    }

    @Override
    public OliviaCrimsonBrideAbility copy() {
        return new OliviaCrimsonBrideAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(filter, getSourceId(), getControllerId(), game) < 1;
    }

    @Override
    public String getRule() {
        return "When you don't control a legendary Vampire, exile this creature.";
    }
}
