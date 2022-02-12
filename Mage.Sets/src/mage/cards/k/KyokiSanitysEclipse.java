package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KyokiSanitysEclipse extends CardImpl {

    public KyokiSanitysEclipse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whenever you cast a Spirit or Arcane spell, target opponent exiles a card from their hand.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ExileFromZoneTargetEffect(Zone.HAND, false),
                StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private KyokiSanitysEclipse(final KyokiSanitysEclipse card) {
        super(card);
    }

    @Override
    public KyokiSanitysEclipse copy() {
        return new KyokiSanitysEclipse(this);
    }
}
