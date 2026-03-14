package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerraMagicalAdept extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledEnchantmentPermanent("nonlegendary enchantment you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public TerraMagicalAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD, SubType.WARRIOR}, "{1}{R}{G}",
                "Esper Terra",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.WIZARD}, "RG"
        );

        // Terra, Magical Adept
        this.getLeftHalfCard().setPT(4, 2);

        // When Terra enters, mill five cards. Put up to one enchantment milled this this way into your hand.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                5, StaticFilters.FILTER_CARD_ENCHANTMENT, true
        ).setText("mill five cards. Put up to one enchantment card milled this way into your hand")));

        // Trance -- {4}{R}{G}, {T}: Exile Terra, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{4}{R}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability.withFlavorWord("Trance"));

        // Esper Terra
        this.getRightHalfCard().setPT(6, 6);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard(), SagaChapter.CHAPTER_IV);

        // I, II, III -- Create a token that's a copy of target nonlegendary enchantment you control. It gains haste. If it's a Saga, put up to three lore counters on it. Sacrifice it at the beginning of your next end step.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new EsperTerraEffect(), new TargetPermanent(filter)
        );

        // IV -- Add {W}{W}, {U}{U}, {B}{B}, {R}{R}, and {G}{G}. Exile Esper Terra, then return it to the battlefield.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_IV,
                new BasicManaEffect(new Mana(
                        2, 2, 2, 2, 2, 0, 0, 0
                )).setText("add {W}{W}, {U}{U}, {B}{B}, {R}{R}, and {G}{G}"),
                new ExileSourceAndReturnFaceUpEffect());
        this.getRightHalfCard().addAbility(sagaAbility);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private TerraMagicalAdept(final TerraMagicalAdept card) {
        super(card);
    }

    @Override
    public TerraMagicalAdept copy() {
        return new TerraMagicalAdept(this);
    }
}

class EsperTerraEffect extends OneShotEffect {

    EsperTerraEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target nonlegendary enchantment you control. " +
                "It gains haste. If it's a Saga, put up to three lore counters on it. " +
                "Sacrifice it at the beginning of your next end step";
    }

    private EsperTerraEffect(final EsperTerraEffect effect) {
        super(effect);
    }

    @Override
    public EsperTerraEffect copy() {
        return new EsperTerraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setSavedPermanent(permanent);
        effect.addAdditionalAbilities(HasteAbility.getInstance());
        effect.apply(game, source);
        for (Permanent token : effect.getAddedPermanents()) {
            if (!token.hasSubtype(SubType.SAGA, game)) {
                continue;
            }
            Optional.ofNullable(source.getControllerId())
                    .map(game::getPlayer)
                    .map(player -> player.getAmount(
                            0, 3, "Choose how many lore counters to put on " + token.getIdName(), source, game
                    ))
                    .filter(amount -> amount > 0)
                    .ifPresent(amount -> token.addCounters(CounterType.LORE.createInstance(amount), source, game));
        }
        effect.removeTokensCreatedAt(game, source, false, PhaseStep.END_TURN, TargetController.YOU);
        return true;
    }
}
