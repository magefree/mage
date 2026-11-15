package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SculptureTreasureToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class VraskaSoulOfStone extends CardImpl {

    public VraskaSoulOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Artifact creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // Whenever you cast a noncreature spell, create a 1/1 colorless Sculpture Treasure artifact creature token with "{T}, Sacrifice this token: Add mana of any color."
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CreateTokenEffect(new SculptureTreasureToken()), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private VraskaSoulOfStone(final VraskaSoulOfStone card) {
        super(card);
    }

    @Override
    public VraskaSoulOfStone copy() {
        return new VraskaSoulOfStone(this);
    }
}
