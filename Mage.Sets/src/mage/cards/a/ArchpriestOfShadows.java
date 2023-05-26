package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.BackupAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchpriestOfShadows extends CardImpl {

    public ArchpriestOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Deathtouch
        backupAbility.addAbility(DeathtouchAbility.getInstance());

        // Whenever this creature deals combat damage to a player or battle, return target creature card from your graveyard to the battlefield.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        ).setOrBattle(true).setTriggerPhrase("Whenever this creature deals combat damage to a player or battle, ");
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        backupAbility.addAbility(ability);
    }

    private ArchpriestOfShadows(final ArchpriestOfShadows card) {
        super(card);
    }

    @Override
    public ArchpriestOfShadows copy() {
        return new ArchpriestOfShadows(this);
    }
}
