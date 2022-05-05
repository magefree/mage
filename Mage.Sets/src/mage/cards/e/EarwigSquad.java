package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ProwlCostWasPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
import mage.abilities.hint.common.ProwlCostWasPaidHint;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EarwigSquad extends CardImpl {

    public EarwigSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Prowl {2}{B}
        this.addAbility(new ProwlAbility(this, "{2}{B}"));

        // When Earwig Squad enters the battlefield, if its prowl cost was paid, search target opponent's library for three cards and exile them. Then that player shuffles their library.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(
                        new SearchLibraryAndExileTargetEffect(3, true), false
                ), ProwlCostWasPaidCondition.instance, "When {this} enters the battlefield, " +
                "if its prowl cost was paid, search target opponent's library for three cards " +
                "and exile them. Then that player shuffles."
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(ProwlCostWasPaidHint.instance));

    }

    private EarwigSquad(final EarwigSquad card) {
        super(card);
    }

    @Override
    public EarwigSquad copy() {
        return new EarwigSquad(this);
    }
}
