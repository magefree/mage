package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarrionImp extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public CarrionImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Carrion Imp enters the battlefield, you may exile target creature card from a graveyard. If you do, you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(), true);
        ability.addEffect(new GainLifeEffect(2).concatBy("If you do,"));
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private CarrionImp(final CarrionImp card) {
        super(card);
    }

    @Override
    public CarrionImp copy() {
        return new CarrionImp(this);
    }
}
