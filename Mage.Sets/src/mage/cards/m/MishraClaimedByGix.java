package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.MeldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishraClaimedByGix extends CardImpl {

    private static final DynamicValue xValue = new AttackingCreatureCount();
    private static final Condition condition = new MeldCondition(
            "Phyrexian Dragon Engine", CardType.CREATURE, true
    );

    public MishraClaimedByGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.meldsWithClazz = mage.cards.p.PhyrexianDragonEngine.class;
        this.meldsToClazz = mage.cards.m.MishraLostToPhyrexia.class;

        // Whenever you attack, each opponent loses X life and you gain X life, where X is the number of attacking creatures. If Mishra, Claimed by Gix and a creature named Phyrexian Dragon Engine are attacking, and you both own and control them, exile them, then meld them into Mishra, Lost to Phyrexia. It enters the battlefield tapped and attacking.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new LoseLifeOpponentsEffect(xValue).setText("each opponent loses X life"), 1
        );
        ability.addEffect(new GainLifeEffect(xValue)
                .setText("and you gain X life, where X is the number of attacking creatures."));
        ability.addEffect(new ConditionalOneShotEffect(
                new MeldEffect("Phyrexian Dragon Engine", "Mishra, Lost to Phyrexia", true),
                condition, "If {this} and a creature named Phyrexian Dragon Engine are attacking, " +
                "and you both own and control them, exile them, then meld them into Mishra, Lost to Phyrexia. " +
                "It enters the battlefield tapped and attacking"
        ));
        this.addAbility(ability);
    }

    private MishraClaimedByGix(final MishraClaimedByGix card) {
        super(card);
    }

    @Override
    public MishraClaimedByGix copy() {
        return new MishraClaimedByGix(this);
    }
}
