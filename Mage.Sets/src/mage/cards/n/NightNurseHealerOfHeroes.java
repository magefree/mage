package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.card.PutIntoGraveFromAnywhereThisTurnPredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NightNurseHealerOfHeroes extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard(
        "permanent card in your graveyard that was put there from anywhere this turn"
    );

    static {
        filter.add(PutIntoGraveFromAnywhereThisTurnPredicate.instance);
    }

    public NightNurseHealerOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Night Nurse enters, choose target permanent card in your graveyard that was put there from anywhere this turn. Return it to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new ReturnFromGraveyardToHandTargetEffect()
                .setText("choose target permanent card in your graveyard that was put there from anywhere this turn. Return it to your hand")
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability, new CardsPutIntoGraveyardWatcher());
    }

    private NightNurseHealerOfHeroes(final NightNurseHealerOfHeroes card) {
        super(card);
    }

    @Override
    public NightNurseHealerOfHeroes copy() {
        return new NightNurseHealerOfHeroes(this);
    }
}
