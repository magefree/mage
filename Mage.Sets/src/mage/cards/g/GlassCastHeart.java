package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.BloodToken;
import mage.game.permanent.token.EdgarMarkovsCoffinToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlassCastHeart extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.VAMPIRE, "");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.BLOOD, "Blood token");

    static {
        filter2.add(TokenPredicate.TRUE);
    }

    public GlassCastHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        // Whenever one or more Vampires you control attack, create a Blood token.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new BloodToken()), 1, filter
        ).setTriggerPhrase("Whenever one or more Vampires you control attack, "));

        // {B}, {T}, Pay 1 life: Create a 1/1 white and black Vampire creature token with lifelink.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new EdgarMarkovsCoffinToken()), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {B}{B}, {T}, Sacrifice Glass-Cast Heart and thirteen Blood tokens: Each opponent loses 13 life and you gain 13 life.
        ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(13), new ManaCostsImpl<>("{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new CompositeCost(
                new SacrificeSourceCost(),
                new SacrificeTargetCost(
                        new TargetControlledPermanent(13, filter2)
                ), "sacrifice {this} and thirteen Blood tokens"
        ));
        ability.addEffect(new GainLifeEffect(13).concatBy("and"));
        this.addAbility(ability);
    }

    private GlassCastHeart(final GlassCastHeart card) {
        super(card);
    }

    @Override
    public GlassCastHeart copy() {
        return new GlassCastHeart(this);
    }
}
