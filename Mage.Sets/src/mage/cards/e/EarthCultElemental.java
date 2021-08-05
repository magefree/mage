package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthCultElemental extends CardImpl {

    public EarthCultElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Siege Monster — When Earth-Cult Elemental enters the battlefield, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect).withFlavorWord("Siege Monster"));

        // 1-9 | Each player sacrifices a permanent.
        effect.addTableEntry(1, 9, new SacrificeAllEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_SHORT_TEXT));

        // 10-19 | Each opponent sacrifices a permanent.
        effect.addTableEntry(10, 19, new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT));

        // 20 | Each opponent sacrifices two permanents.
        effect.addTableEntry(20, 20, new SacrificeOpponentsEffect(2, StaticFilters.FILTER_PERMANENTS));
    }

    private EarthCultElemental(final EarthCultElemental card) {
        super(card);
    }

    @Override
    public EarthCultElemental copy() {
        return new EarthCultElemental(this);
    }
}
