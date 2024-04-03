package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordOfWealthAndPower extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instants and from sorceries");

    public SwordOfWealthAndPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from instants and from sorceries.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(new ProtectionAbility(filter), AttachmentType.EQUIPMENT));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, create a Treasure token. When you next cast an instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), "equipped", false
        );
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private SwordOfWealthAndPower(final SwordOfWealthAndPower card) {
        super(card);
    }

    @Override
    public SwordOfWealthAndPower copy() {
        return new SwordOfWealthAndPower(this);
    }
}
