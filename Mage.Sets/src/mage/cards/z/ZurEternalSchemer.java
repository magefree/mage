package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ZurEternalSchemer extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Enchantment creatures");

    private static final FilterEnchantmentPermanent filter2
            = new FilterEnchantmentPermanent("non-Aura enchantment you control");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter2.add(Predicates.not(SubType.AURA.getPredicate()));
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public ZurEternalSchemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Enchantment creatures you control have deathtouch, lifelink, and hexproof.
        CompoundAbility compoundAbilities = new CompoundAbility(DeathtouchAbility.getInstance(), LifelinkAbility.getInstance(), HexproofAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(compoundAbilities, Duration.WhileOnBattlefield, filter)));

        // {1}{W}: Target non-Aura enchantment you control becomes a creature in addition to its other types and has base power and toughness each equal to its mana value.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZurEternalSchemerEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private ZurEternalSchemer(final ZurEternalSchemer card) {
        super (card);
    }

    @Override
    public ZurEternalSchemer copy() {
        return new ZurEternalSchemer(this);
    }
}

class ZurEternalSchemerEffect extends ContinuousEffectImpl {

    public ZurEternalSchemerEffect() {
        super(Duration.EndOfGame, Outcome.BecomeCreature);
        staticText = "Target non-Aura enchantment you control becomes a creature in addition to its other types " +
                "and has base power and base toughness each equal to its mana value.";
    }

    public ZurEternalSchemerEffect(final ZurEternalSchemerEffect effect) {
        super(effect);
    }

    @Override
    public ZurEternalSchemerEffect copy() {
        return new ZurEternalSchemerEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (enchantment == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    if (!enchantment.isCreature(game)) {
                        enchantment.addCardType(game, CardType.CREATURE);
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    int cmc = enchantment.getManaValue();
                    enchantment.getPower().setModifiedBaseValue(cmc);
                    enchantment.getToughness().setModifiedBaseValue(cmc);
                }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.TypeChangingEffects_4;
    }
}
