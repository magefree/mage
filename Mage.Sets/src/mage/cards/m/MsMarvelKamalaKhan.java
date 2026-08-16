package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author muz
 */
public final class MsMarvelKamalaKhan extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets a creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public MsMarvelKamalaKhan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.INHUMAN);
        this.subtype.add(SubType.HERO);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
            Integer.MAX_VALUE, Duration.WhileOnBattlefield,
            MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // Embiggen Fist -- Whenever you cast a spell that targets a creature you control, draw a card. Until end of turn, Ms. Marvel gains "Ms. Marvel's base power is equal to the number of cards in your hand."
        Ability ability = new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, false);
        ability.addEffect(new GainAbilitySourceEffect(
            new SimpleStaticAbility(new MsMarvelKamalaKhanEffect()), Duration.EndOfTurn
        ).setText("Until end of turn, {this} gains \"{this}'s base power is equal to the number of cards in your hand.\""));
        this.addAbility(ability.withFlavorWord("Embiggen Fist"));
    }

    private MsMarvelKamalaKhan(final MsMarvelKamalaKhan card) {
        super(card);
    }

    @Override
    public MsMarvelKamalaKhan copy() {
        return new MsMarvelKamalaKhan(this);
    }
}

class MsMarvelKamalaKhanEffect extends ContinuousEffectImpl {

    MsMarvelKamalaKhanEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "{this}'s base power is equal to the number of cards in your hand";
    }

    private MsMarvelKamalaKhanEffect(final MsMarvelKamalaKhanEffect effect) {
        super(effect);
    }

    @Override
    public MsMarvelKamalaKhanEffect copy() {
        return new MsMarvelKamalaKhanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = source.getSourcePermanentIfItStillExists(game);
        if (mageObject == null) {
            discard();
            return false;
        }
        mageObject.getPower().setModifiedBaseValue(CardsInControllerHandCount.ANY.calculate(game, source, this));
        return true;
    }
}
