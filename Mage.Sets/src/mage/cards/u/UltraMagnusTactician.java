package mage.cards.u;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltraMagnusTactician extends TransformingDoubleFacedCard {

    public UltraMagnusTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{4}{R}{G}{W}",
                "Ultra Magnus, Armored Carrier",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "RGW"
        );

        // Ultra Magnus, Tactician
        this.getLeftHalfCard().setPT(7, 7);

        // More Than Meets the Eye {2}{R}{G}{W}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{R}{G}{W}"));

        // Ward {2}
        this.getLeftHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Whenever Ultra Magnus attacks, you may put an artifact creature card from your hand onto the battlefield tapped and attacking. If you do, convert Ultra Magnus at end of combat.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new UltraMagnusTacticianEffect()));

        // Ultra Magnus, Armored Carrier
        this.getRightHalfCard().setPT(4, 7);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Formidable -- Whenever Ultra Magnus attacks, attacking creatures you control gain indestructible until end of turn. If those creatures have total power 8 or greater, convert Ultra Magnus.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), UltraMagnusArmoredCarrierCondition.instance,
                "If those creatures have total power 8 or greater, convert {this}"
        ));
        this.getRightHalfCard().addAbility(ability.setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private UltraMagnusTactician(final UltraMagnusTactician card) {
        super(card);
    }

    @Override
    public UltraMagnusTactician copy() {
        return new UltraMagnusTactician(this);
    }
}

class UltraMagnusTacticianEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard("artifact creature card");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    UltraMagnusTacticianEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may put an artifact creature card from your hand onto the battlefield " +
                "tapped and attacking. If you do, convert {this} at end of combat";
    }

    private UltraMagnusTacticianEffect(final UltraMagnusTacticianEffect effect) {
        super(effect);
    }

    @Override
    public UltraMagnusTacticianEffect copy() {
        return new UltraMagnusTacticianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, filter);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return true;
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
                new TransformSourceEffect().setText("convert {this}")
        ), source);
        return true;
    }
}

enum UltraMagnusArmoredCarrierCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterAttackingCreature();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum() >= 8;
    }
}
