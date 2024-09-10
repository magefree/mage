package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.RiotAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GruulSpellbreaker extends CardImpl {

    public GruulSpellbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Riot
        this.addAbility(new RiotAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As long as it's your turn, you and Gruul Spellbreaker have hexproof.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilityControllerEffect(
                                HexproofAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ), MyTurnCondition.instance, "As long as it's your turn, you"
                )
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        HexproofAbility.getInstance(),
                        Duration.WhileOnBattlefield
                ), MyTurnCondition.instance, "and {this} have hexproof."
        ));
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private GruulSpellbreaker(final GruulSpellbreaker card) {
        super(card);
    }

    @Override
    public GruulSpellbreaker copy() {
        return new GruulSpellbreaker(this);
    }
}
