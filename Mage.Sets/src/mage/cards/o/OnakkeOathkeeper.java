package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OnakkeOathkeeper extends CardImpl {

    public OnakkeOathkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Creatures can't attack planeswalkers you control unless their controller pays {1} for each creature they control that's attacking a planeswalker you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new CantAttackYouUnlessPayAllEffect(
                Duration.WhileOnBattlefield,
                new ManaCostsImpl<>("{1}"),
                CantAttackYouUnlessPayAllEffect.Scope.CONTROLLED_PLANESWALKERS_ONLY
            )
        ));

        // {4}{W}{W}, Exile Onakke Oathkeeper from your graveyard: Return target planeswalker card from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{4}{W}{W}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterPlaneswalkerCard()));
        this.addAbility(ability);
    }

    private OnakkeOathkeeper(final OnakkeOathkeeper card) {
        super(card);
    }

    @Override
    public OnakkeOathkeeper copy() {
        return new OnakkeOathkeeper(this);
    }
}
