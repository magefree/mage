package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetadjustment.DamagedPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class TinybonesThePickpocket extends CardImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("nonland permanent card from that player's graveyard");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public TinybonesThePickpocket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Tinybones, the Pickpocket deals combat damage to a player, you may cast target nonland permanent card from that player's graveyard, and mana of any type can be spent to cast that spell.
        OneShotEffect effect = new MayCastTargetCardEffect(CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE, false);
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetCardInGraveyard(filter));
        ability.setTargetAdjuster(new DamagedPlayerControlsTargetAdjuster(true));
        this.addAbility(ability);
    }

    private TinybonesThePickpocket(final TinybonesThePickpocket card) {
        super(card);
    }

    @Override
    public TinybonesThePickpocket copy() {
        return new TinybonesThePickpocket(this);
    }
}
