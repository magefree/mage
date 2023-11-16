package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class IllithidHarvester extends AdventureCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped nontoken creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TokenPredicate.FALSE);
    }

    public IllithidHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{U}", "Plant Tadpoles", "{X}{U}{U}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ceremorphosis — When Illithid Harvester enters the battlefield, turn any number
        // of target tapped nontoken creatures face down. They're 2/2 Horror creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new IllithidHarvesterEffect());
        ability.addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, false));
        this.addAbility(ability.withFlavorWord("Ceremorphosis"));

        // Plant Tadpoles
        // Tap X target creatures. They don't untap during their controllers' next untap steps.
        this.getSpellCard().getSpellAbility().addEffect(new TapTargetEffect("tap X target creatures"));
        this.getSpellCard().getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect()
                .setText("They don't untap during their controllers' next untap steps"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellCard().getSpellAbility().setTargetAdjuster(IllithidHarvesterAdjuster.instance);

        this.finalizeAdventure();
    }

    private IllithidHarvester(final IllithidHarvester card) {
        super(card);
    }

    @Override
    public IllithidHarvester copy() {
        return new IllithidHarvester(this);
    }
}

enum IllithidHarvesterAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetCreaturePermanent(xValue, xValue));
    }
}

class IllithidHarvesterEffect extends OneShotEffect {

    public IllithidHarvesterEffect() {
        super(Outcome.Detriment);
        this.staticText = "turn any number of target tapped nontoken creatures face down. They're 2/2 Horror creatures";
    }

    private IllithidHarvesterEffect(final IllithidHarvesterEffect effect) {
        super(effect);
    }

    @Override
    public IllithidHarvesterEffect copy() {
        return new IllithidHarvesterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate<Permanent> pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (!game.getPermanent(targetId).isTransformable()) {
                    pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
                }
            }
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(pred);

        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        game.addEffect(new BecomesSubtypeAllEffect(Duration.WhileOnBattlefield, Arrays.asList(SubType.HORROR), filter, false), source);
        return true;
    }
}
