package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StirringBard extends CardImpl {

    public StirringBard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Stirring Bard enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Mantle of Inspiration — {T}: Target creature gains menace and haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(new MenaceAbility(false))
                        .setText("target creature gains menace"),
                new TapSourceCost()
        );
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and haste until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Mantle of Inspiration"));
    }

    private StirringBard(final StirringBard card) {
        super(card);
    }

    @Override
    public StirringBard copy() {
        return new StirringBard(this);
    }
}
