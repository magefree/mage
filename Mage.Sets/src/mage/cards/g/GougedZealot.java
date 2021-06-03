package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.constants.*;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class GougedZealot extends CardImpl {

    public GougedZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Delirium — Whenever Gouged Zealot attacks, if there are four or more card types among cards in your graveyard, Gouged Zealot deals 1 damage to each creature defending player controls.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new GougedZealotEffect(), false, null, SetTargetPointer.PLAYER),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; Whenever {this} attacks, if there are four or more card types among cards in your graveyard, {this} deals 1 damage to each creature defending player controls."
        ).addHint(CardTypesInGraveyardHint.YOU));
    }

    private GougedZealot(final GougedZealot card) {
        super(card);
    }

    @Override
    public GougedZealot copy() {
        return new GougedZealot(this);
    }
}

class GougedZealotEffect extends OneShotEffect {

    public GougedZealotEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each creature defending player controls";
    }

    private GougedZealotEffect(final GougedZealotEffect effect) {
        super(effect);
    }

    @Override
    public GougedZealotEffect copy() {
        return new GougedZealotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = getTargetPointer().getFirst(game, source);
        if (defendingPlayerId != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, defendingPlayerId, game)) {
                permanent.damage(1, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
