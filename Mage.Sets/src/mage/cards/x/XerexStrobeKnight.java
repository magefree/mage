package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.KnightWhiteBlueToken;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class XerexStrobeKnight extends CardImpl {

    public XerexStrobeKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Create a 2/2 white and blue Knight creature token with vigilance. Activate only if you've cast two or more spells this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new KnightWhiteBlueToken()),
                new TapSourceCost(), XerexStrobeKnightCondition.instance
        ));
    }

    private XerexStrobeKnight(final XerexStrobeKnight card) {
        super(card);
    }

    @Override
    public XerexStrobeKnight copy() {
        return new XerexStrobeKnight(this);
    }
}

enum XerexStrobeKnightCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) >= 2;
    }

    @Override
    public String toString() {
        return "you've cast two or more spells this turn";
    }
}