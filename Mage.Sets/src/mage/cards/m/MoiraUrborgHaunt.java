package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MoiraUrborgHaunt extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card in your graveyard that was put there from the battlefield this turn"
    );

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public MoiraUrborgHaunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Moira, Urborg Haunt deals combat damage to a player, return to the battlefield target
        // creature card in your graveyard that was put there from the battlefield this turn.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(false)
                        .setText("return to the battlefield target creature card" +
                                " in your graveyard that was put there from the battlefield this turn."),
                false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addWatcher(new CardsPutIntoGraveyardWatcher());
        this.addAbility(ability);
    }

    private MoiraUrborgHaunt(final MoiraUrborgHaunt card) {
        super(card);
    }

    @Override
    public MoiraUrborgHaunt copy() {
        return new MoiraUrborgHaunt(this);
    }
}
