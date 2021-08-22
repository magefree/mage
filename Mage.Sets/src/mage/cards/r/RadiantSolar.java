package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantSolar extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public RadiantSolar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Radiant Solar or another nontoken creature enters the battlefield under your control, venture into the dungeon.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new VentureIntoTheDungeonEffect(), filter, false, true
        ));

        // {W}, Discard Radiant Solar: Venture into the dungeon and you gain 3 life.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new VentureIntoTheDungeonEffect().setText("venture into the dungeon"), new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        this.addAbility(ability);
    }

    private RadiantSolar(final RadiantSolar card) {
        super(card);
    }

    @Override
    public RadiantSolar copy() {
        return new RadiantSolar(this);
    }
}
