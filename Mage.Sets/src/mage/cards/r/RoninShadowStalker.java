package mage.cards.r;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.*;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class RoninShadowStalker extends CardImpl {

    private static final FilterControlledPermanent filterEquipment = new FilterControlledPermanent(SubType.EQUIPMENT, "an Equipment attached to {this}");

    static {
        filterEquipment.add(AttachedToSourcePredicate.instance);
    }

    public RoninShadowStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Pay 2 life: Add two mana of any one color. Spend this mana only to cast Equipment spells or activate equip abilities. Activate only once each turn.
        this.addAbility(new RoninShadowStalkerManaAbility());

        // {T}, Sacrifice an Equipment attached to Ronin: Target creature gets -4/-4 until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new BoostTargetEffect(-4, -4), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filterEquipment));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RoninShadowStalker(final RoninShadowStalker card) {
        super(card);
    }

    @Override
    public RoninShadowStalker copy() {
        return new RoninShadowStalker(this);
    }
}

class RoninShadowStalkerManaAbility extends ConditionalAnyColorManaAbility {

    RoninShadowStalkerManaAbility() {
        super(new PayLifeCost(2), 2, new RoninShadowStalkerManaBuilder(), true);
        setMaxActivationsPerTurn(1);
    }

    private RoninShadowStalkerManaAbility(final RoninShadowStalkerManaAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only once each turn.";
    }

    @Override
    public RoninShadowStalkerManaAbility copy() {
        return new RoninShadowStalkerManaAbility(this);
    }
}

class RoninShadowStalkerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new RoninShadowStalkerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Equipment spells or activate equip abilities";
    }
}

class RoninShadowStalkerConditionalMana extends ConditionalMana {

    RoninShadowStalkerConditionalMana(Mana mana) {
        super(mana);
        addCondition(RoninShadowStalkerEquipCondition.instance);
    }
}

enum RoninShadowStalkerEquipCondition implements Condition {
    instance;

    private static final FilterSpell filter = new FilterSpell("Equipment spells");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.isActivated()) {
            return false;
        }
        if (source instanceof EquipAbility) {
            return true;
        }
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            if (object instanceof StackObject) {
                return filter.match((StackObject) object, source.getControllerId(), source, game);
            }
            // checking mana without real cast
            if (game.inCheckPlayableState()) {
                Spell spell = null;
                if (object instanceof Card) {
                    spell = new Spell((Card) object, (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                } else if (object instanceof Commander) {
                    spell = new Spell(((Commander) object).getSourceObject(), (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                }
                return filter.match(spell, source.getControllerId(), source, game);
            }
        }
        return false;
    }
}
