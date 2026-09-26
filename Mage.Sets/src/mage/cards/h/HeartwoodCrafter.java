package mage.cards.h;

import java.util.UUID;
import mage.ConditionalMana;
import mage.Mana;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.AddConditionalColorlessManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.HeartwoodToken;
import mage.game.stack.Spell;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HeartwoodCrafter extends PrepareCard {

    public HeartwoodCrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}", "Soul Tether", new CardType[]{CardType.SORCERY}, "{2}{R/G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // {T}: Add {C}. This mana can't be spent to cast spells from your hand.
        this.addAbility(new SimpleManaAbility(
            new AddConditionalColorlessManaEffect(1, new HeartwoodCrafterManaBuilder()), new TapSourceCost()
        ));

        // Soul Tether
        // Sorcery {2}{R/G}
        // Create a Heartwood token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new HeartwoodToken()));
    }

    private HeartwoodCrafter(final HeartwoodCrafter card) {
        super(card);
    }

    @Override
    public HeartwoodCrafter copy() {
        return new HeartwoodCrafter(this);
    }
}

class HeartwoodCrafterManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new HeartwoodCrafterConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "This mana can't be spent to cast spells from your hand";
    }
}

class HeartwoodCrafterConditionalMana extends ConditionalMana {

    HeartwoodCrafterConditionalMana(Mana mana) {
        super(mana);
        staticText = "This mana can't be spent to cast spells from your hand";
        addCondition(HeartwoodCrafterManaCondition.instance);
    }
}

enum HeartwoodCrafterManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return true;
        }
        MageObject object = game.getObject(source);
        if (!source.isControlledBy(game.getOwnerId(object))) {
            return false;
        }
        if (object instanceof Spell) {
            return ((Spell) object).getFromZone() != Zone.HAND;
        }
        // checking mana without real cast
        return game.inCheckPlayableState() && game.getState().getZone(source.getSourceId()) != Zone.HAND;
    }
}
