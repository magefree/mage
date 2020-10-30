package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class UmaraMystic extends CardImpl {

    public UmaraMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant, sorcery, or Wizard spell, Umara Mystic gets +2/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_INSTANT_SORCERY_WIZARD, false
        ));
    }

    private UmaraMystic(final UmaraMystic card) {
        super(card);
    }

    @Override
    public UmaraMystic copy() {
        return new UmaraMystic(this);
    }
}
