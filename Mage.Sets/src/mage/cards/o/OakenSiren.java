package mage.cards.o;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OakenSiren extends CardImpl {

    public OakenSiren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add {U}. Spend this mana only to cast an artifact spell or activate an ability of an artifact source.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(1), new OakenSirenManaBuilder()));
    }

    private OakenSiren(final OakenSiren card) {
        super(card);
    }

    @Override
    public OakenSiren copy() {
        return new OakenSiren(this);
    }
}

class OakenSirenManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new OakenSirenConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or activate an ability of an artifact source";
    }
}

class OakenSirenConditionalMana extends ConditionalMana {

    OakenSirenConditionalMana(Mana mana) {
        super(mana);
        addCondition(OakenSirenCondition.instance);
    }
}

enum OakenSirenCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }
}
