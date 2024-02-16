package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ThunderkinAwakener extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("Elemental creature card in your graveyard with lesser toughness");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
        filter.add(ThunderkinAwakenerPredicate.instance);
    }

    public ThunderkinAwakener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Thunderkin Awakener attacks, choose target Elemental creature card in your graveyard
        // with toughness less than Thunderkin Awakener's toughness. Return that card to the battlefield tapped and attacking.
        // Sacrifice it at the beginning of the next end step.
        Ability ability = new AttacksTriggeredAbility(new ThunderkinAwakenerEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ThunderkinAwakener(final ThunderkinAwakener card) {
        super(card);
    }

    @Override
    public ThunderkinAwakener copy() {
        return new ThunderkinAwakener(this);
    }
}

enum ThunderkinAwakenerPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null
                && input.getObject().getToughness().getValue() < sourcePermanent.getToughness().getValue();
    }
}

class ThunderkinAwakenerEffect extends OneShotEffect {

    ThunderkinAwakenerEffect() {
        super(Outcome.Benefit);
        staticText = "choose target Elemental creature card in your graveyard with toughness less than {this}'s toughness."
                + " Return that card to the battlefield tapped and attacking. Sacrifice it at the beginning of the next end step";
    }

    private ThunderkinAwakenerEffect(final ThunderkinAwakenerEffect effect) {
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
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect(true, true);
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
