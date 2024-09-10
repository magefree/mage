package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarnessedSnubhorn extends CardImpl {

    private static final FilterCard filter
            = new FilterArtifactOrEnchantmentCard("artifact or enchantment card from your graveyard");

    public HarnessedSnubhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Harnessed Snubhorn deals combat damage to a player, return target artifact or enchantment card from your graveyard to the battlefield.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private HarnessedSnubhorn(final HarnessedSnubhorn card) {
        super(card);
    }

    @Override
    public HarnessedSnubhorn copy() {
        return new HarnessedSnubhorn(this);
    }
}
