package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class SeekerOfInsight extends CardImpl {

    public SeekerOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Draw a card, then discard a card. Activate this ability only if you've cast a noncreature spell this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new DrawDiscardControllerEffect(), new TapSourceCost(), SeekerOfInsightCondition.instance
        ));
    }

    private SeekerOfInsight(final SeekerOfInsight card) {
        super(card);
    }

    @Override
    public SeekerOfInsight copy() {
        return new SeekerOfInsight(this);
    }
}

enum SeekerOfInsightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .anyMatch(spell -> !spell.isCreature(game));
    }

    @Override
    public String toString() {
        return "you've cast a noncreature spell this turn";
    }
}
