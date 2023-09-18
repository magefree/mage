package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SearchPartyCaptain extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Creatures you attacked with this turn", SearchPartyCaptainValue.instance
    );

    public SearchPartyCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This spell costs {1} less to cast for each creature you attacked with this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(SearchPartyCaptainValue.instance)
                .setText("this spell costs {1} less to cast for each creature you attacked with this turn")
        ).addHint(hint), new PlayerAttackedWatcher());

        // When Search Party Captain enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private SearchPartyCaptain(final SearchPartyCaptain card) {
        super(card);
    }

    @Override
    public SearchPartyCaptain copy() {
        return new SearchPartyCaptain(this);
    }
}

enum SearchPartyCaptainValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(PlayerAttackedWatcher.class)
                .getNumberOfAttackersCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public SearchPartyCaptainValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}