package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UmaraWizard extends CardImpl {

    public UmaraWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.u.UmaraSkyfalls.class;

        // Whenever you cast an instant, sorcery, or Wizard spell, Umara Wizard gains flying until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_INSTANT_SORCERY_WIZARD, false
        ));
    }

    private UmaraWizard(final UmaraWizard card) {
        super(card);
    }

    @Override
    public UmaraWizard copy() {
        return new UmaraWizard(this);
    }
}
