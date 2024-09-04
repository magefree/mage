package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DraftFromSpellbookThenExilePlayThisTurnEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class ArmsScavenger extends CardImpl {

    private static final List<String> spellbook = Collections.unmodifiableList(Arrays.asList(
            "Boots of Speed",
            "Ceremonial Knife",
            "Cliffhaven Kitesail",
            "Colossus Hammer",
            "Dueling Rapier",
            "Goldvein Pick",
            "Jousting Lance",
            "Mask of Immolation",
            "Mirror Shield",
            "Relic Axe",
            "Rogue's Gloves",
            "Scavenged Blade",
            "Shield of the Realm",
            "Spare Dagger",
            "Tormentor's Helm"
    ));

    public ArmsScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);


        // At the beginning of your upkeep, draft a card from Arms Scavenger’s spellbook, then exile it. Until end of turn, you may play that card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DraftFromSpellbookThenExilePlayThisTurnEffect(spellbook), TargetController.YOU, false));


        // Equip abilities you activate cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(
                EquipAbility.class, "Equip", 1
        ).setText("equip abilities you activate cost {1} less to activate")));

    }

    private ArmsScavenger(final ArmsScavenger card) {
        super(card);
    }

    @Override
    public ArmsScavenger copy() {
        return new ArmsScavenger(this);
    }
}
