package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BlackWidowDoubleAgent extends CardImpl {

    public BlackWidowDoubleAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature you control attacks alone, it gains first strike and menace until end of turn.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
            new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()).setText("it gains first strike"),
            true, false
        );
        ability.addEffect(new GainAbilityTargetEffect(new MenaceAbility(true)).setText("and menace until end of turn"));
        this.addAbility(ability);
    }

    private BlackWidowDoubleAgent(final BlackWidowDoubleAgent card) {
        super(card);
    }

    @Override
    public BlackWidowDoubleAgent copy() {
        return new BlackWidowDoubleAgent(this);
    }
}
