package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChampionOfStraySouls extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ChampionOfStraySouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        /**
         * You choose the targets of the first ability as you activate that
         * ability, before you pay any costs. You can't target any of the
         * creatures you sacrifice.
         */
        // {3}{B}{B}, {T}, Sacrifice X other creatures: Return X target creatures from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return X target creature cards from your graveyard to the battlefield");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{3}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeXTargetCost(filter));
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        ability.setTargetAdjuster(ChampionOfStraySoulsAdjuster.instance);
        this.addAbility(ability);

        // {5}{B}{B}: Put Champion of Stray Souls on top of your library from your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD,
                new PutOnLibrarySourceEffect(true, "Put {this} on top of your library from your graveyard"),
                new ManaCostsImpl("{5}{B}{B}")));
    }

    private ChampionOfStraySouls(final ChampionOfStraySouls card) {
        super(card);
    }

    @Override
    public ChampionOfStraySouls copy() {
        return new ChampionOfStraySouls(this);
    }
}

enum ChampionOfStraySoulsAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        for (Effect effect : ability.getEffects()) {
            if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                int xValue = GetXValue.instance.calculate(game, ability, null);
                ability.getTargets().clear();
                ability.addTarget(new TargetCardInYourGraveyard(xValue, xValue, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
            }
        }
    }
}
