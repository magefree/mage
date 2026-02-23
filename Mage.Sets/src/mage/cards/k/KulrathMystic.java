package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class KulrathMystic extends CardImpl {

    public KulrathMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you cast a spell with mana value 4 or greater, this creature gets +2/+0 and gains vigilance until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new BoostSourceEffect(2, 0, Duration.EndOfTurn).setText("this creature gets +2/+0"),
            StaticFilters.FILTER_SPELL_MV_4_OR_GREATER, false
        );
        ability.addEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn).setText("and gains vigilance until end of turn"));
        this.addAbility(ability);
    }

    private KulrathMystic(final KulrathMystic card) {
        super(card);
    }

    @Override
    public KulrathMystic copy() {
        return new KulrathMystic(this);
    }
}
