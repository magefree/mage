package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverquillPledgemage extends CardImpl {

    public SilverquillPledgemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, Silverquill Pledgemage gains your choice of flying or lifelink until end of turn.
        this.addAbility(new MagecraftAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source,
                FlyingAbility.getInstance(), LifelinkAbility.getInstance())));
    }

    private SilverquillPledgemage(final SilverquillPledgemage card) {
        super(card);
    }

    @Override
    public SilverquillPledgemage copy() {
        return new SilverquillPledgemage(this);
    }
}
