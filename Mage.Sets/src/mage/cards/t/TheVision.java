package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class TheVision extends CardImpl {

    public TheVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a noncreature spell, choose one that hasn’t been chosen this turn —
        // • Solar Beam — The Vision gains double strike until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn)
                .setText("{this} gains double strike until end of turn"),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.withFirstModeFlavorWord("Solar Beam");
        ability.setModeTag("double strike");
        ability.getModes().setLimitUsageByOnce(true);

        // • Density Control — The Vision gains indestructible until end of turn.
        ability.addMode(new Mode(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
                .setText("{this} gains indestructible until end of turn"))
                .withFlavorWord("Density Control")
                .setModeTag("indestructible"));

        // • Technopathy — Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1))
                .withFlavorWord("Technopathy")
                .setModeTag("draw a card"));

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private TheVision(final TheVision card) {
        super(card);
    }

    @Override
    public TheVision copy() {
        return new TheVision(this);
    }
}
