package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KessigForgemaster extends CardImpl {

    public KessigForgemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.f.FlameheartWerewolf.class;

        // Whenever Kessig Forgemaster blocks or becomes blocked by a creature, Kessig Forgemaster deals 1 damage to that creature.
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(new DamageTargetEffect(1, true, "that creature"),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, null, true));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Kessig Forgemaster.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());

    }

    private KessigForgemaster(final KessigForgemaster card) {
        super(card);
    }

    @Override
    public KessigForgemaster copy() {
        return new KessigForgemaster(this);
    }
}
