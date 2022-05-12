package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LordOfTheUndead extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie creatures");
    private static final FilterCard filterCard = new FilterCard("Zombie card from your graveyard");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
        filterCard.add(SubType.ZOMBIE.getPredicate());
    }

    public LordOfTheUndead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Zombie creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {1}{B}, {tap}: Return target Zombie card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filterCard));
        this.addAbility(ability);
    }

    private LordOfTheUndead(final LordOfTheUndead card) {
        super(card);
    }

    @Override
    public LordOfTheUndead copy() {
        return new LordOfTheUndead(this);
    }
}
