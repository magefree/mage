package mage.cards.e;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
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
public final class EsperTerra extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledEnchantmentPermanent("nonlegendary enchantment you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public EsperTerra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.nightCard = true;
        this.color.setRed(true);
        this.color.setGreen(true);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Create a token that's a copy of target nonlegendary enchantment you control. It gains haste. If it's a Saga, put up to three lore counters on it. Sacrifice it at the beginning of your next end step.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new EsperTerraEffect(), new TargetPermanent(filter)
        );

        // IV -- Add {W}{W}, {U}{U}, {B}{B}, {R}{R}, and {G}{G}. Exile Esper Terra, then return it to the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new BasicManaEffect(new Mana(
                        2, 2, 2, 2, 2, 0, 0, 0
                )).setText("add {W}{W}, {U}{U}, {B}{B}, {R}{R}, and {G}{G}"),
                new ExileSourceAndReturnFaceUpEffect());
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private EsperTerra(final EsperTerra card) {
        super(card);
    }

    @Override
    public EsperTerra copy() {
        return new EsperTerra(this);
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
