package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenerableWarsinger extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value X or less from your graveyard");

    public VenerableWarsinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Venerable Warsinger deals combat damage to a player, you may return target creature card with mana value X or less from your graveyard to the battlefield, where X is the amount of damage that Venerable Warsinger dealt to that player.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.setTargetAdjuster(new ManaValueTargetAdjuster(new EffectKeyValue("damage"), ComparisonType.OR_LESS));
        ability.addEffect(new InfoEffect("where X is the amount of damage {this} dealt to that player").concatBy(","));
        this.addAbility(ability);
    }

    private VenerableWarsinger(final VenerableWarsinger card) {
        super(card);
    }

    @Override
    public VenerableWarsinger copy() {
        return new VenerableWarsinger(this);
    }
}
