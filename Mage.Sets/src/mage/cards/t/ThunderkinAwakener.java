package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ThunderkinAwakener extends CardImpl {

    public ThunderkinAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Thunderkin Awakener attacks, choose target Elemental creature card in your graveyard
        // with toughness less than Thunderkin Awakener’s toughness. Return that card to the battlefield tapped and attacking.
        // Sacrifice it at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new ThunderkinAwakenerEffect(), false);
        ability.setTargetAdjuster(ThunderkinAwakenerAdjuster.instance);
        this.addAbility(ability);
    }

    public ThunderkinAwakener(final ThunderkinAwakener card) {
        super(card);
    }

    @Override
    public ThunderkinAwakener copy() {
        return new ThunderkinAwakener(this);
    }
}

enum ThunderkinAwakenerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        // target Elemental creature card in your graveyard with toughness less than Thunderkin Awakener’s toughness
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        if (sourcePermanent != null) {
            int xValue = sourcePermanent.getToughness().getValue();
            FilterCard filter = new FilterCreatureCard("creature card in your graveyard with toughness less than Thunderkin Awakener’s toughness");
            filter.add(new SubtypePredicate(SubType.ELEMENTAL));
            filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, xValue + 1));

            ability.getTargets().clear();
            ability.addTarget(new TargetCardInYourGraveyard(filter));
        }
    }
}

class ThunderkinAwakenerEffect extends OneShotEffect {

    public ThunderkinAwakenerEffect() {
        super(Outcome.Benefit);
        staticText = "choose target Elemental creature card in your graveyard with toughness less than Thunderkin Awakener’s toughness."
                + " Return that card to the battlefield tapped and attacking. Sacrifice it at the beginning of the next end step";
    }

    public ThunderkinAwakenerEffect(final ThunderkinAwakenerEffect effect) {
        super(effect);
    }

    @Override
    public ThunderkinAwakenerEffect copy() {
        return new ThunderkinAwakenerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card creatureCard = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (controller != null && creatureCard != null) {

            // Return that card to the battlefield tapped and attacking
            Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect(false, true, true);
            effect.setTargetPointer(new FixedTarget(creatureCard.getId()));
            effect.apply(game, source);

            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                // Sacrifice it at the beginning of the next end step
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice " + permanent.getName(), source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);

                // info
                InfoEffect.addInfoToPermanent(game, source, permanent, "<i><b>Warning</b>: It will be sacrificed at the beginning of the next end step<i>");
            }

            return true;
        }
        return false;
    }

}
