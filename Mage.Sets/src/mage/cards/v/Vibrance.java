package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TwoOfManaColorSpentCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class Vibrance extends CardImpl {

    public Vibrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/G}{R/G}");

        this.subtype.add(SubType.ELEMENTAL, SubType.INCARNATION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, if {R}{R} was spent to cast it, this creature deals 3 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3))
                .withInterveningIf(TwoOfManaColorSpentCondition.RED);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // When this creature enters, if {G}{G} was spent to cast it, search your library for a land card, reveal it, put it into your hand, then shuffle. You gain 2 life.
        ability = new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterLandCard()), true))
                .withInterveningIf(TwoOfManaColorSpentCondition.GREEN);
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);

        // Evoke {R/G}{R/G}
        this.addAbility(new EvokeAbility("{R/G}{R/G}"));
    }

    private Vibrance(final Vibrance card) {
        super(card);
    }

    @Override
    public Vibrance copy() {
        return new Vibrance(this);
    }
}
