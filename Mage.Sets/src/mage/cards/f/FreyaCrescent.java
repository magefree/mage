package mage.cards.f;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FreyaCrescent extends CardImpl {

    public FreyaCrescent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Jump -- During your turn, Freya Crescent has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                MyTurnCondition.instance, "during your turn, {this} has flying"
        )).withFlavorWord("Jump"));

        // {T}: Add {R}. Spend this mana only to cast an Equipment spell or activate an equip ability.
        this.addAbility(new ConditionalColoredManaAbility(Mana.RedMana(1), new FreyaCrescentManaBuilder()));
    }

    private FreyaCrescent(final FreyaCrescent card) {
        super(card);
    }

    @Override
    public FreyaCrescent copy() {
        return new FreyaCrescent(this);
    }
}

class FreyaCrescentManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new FreyaCrescentConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an Equipment spell or activate an equip ability";
    }
}

class FreyaCrescentConditionalMana extends ConditionalMana {

    FreyaCrescentConditionalMana(Mana mana) {
        super(mana);
        addCondition(FreyaCrescentCondition.instance);
    }
}

enum FreyaCrescentCondition implements Condition {
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
            if ((object instanceof StackObject)) {
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
