package mage.cards.c;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.DoombotToken;

/**
 *
 * @author muz
 */
public final class CastleDoom extends CardImpl {

    public CastleDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast an artifact spell.
        this.addAbility(new ConditionalAnyColorManaAbility(
            new TapSourceCost(), 1, new CastleDoomManaBuilder()
        ));

        // {3}, {T}, Sacrifice an artifact: Create a 3/3 colorless Robot Villain artifact creature token named Doombot. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new CreateTokenEffect(new DoombotToken()),
            new ManaCostsImpl<>("{3}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability);
    }

    private CastleDoom(final CastleDoom card) {
        super(card);
    }

    @Override
    public CastleDoom copy() {
        return new CastleDoom(this);
    }
}

class CastleDoomManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CastleDoomConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell";
    }
}

class CastleDoomConditionalMana extends ConditionalMana {
    public CastleDoomConditionalMana(Mana mana) {
        super(mana);
        addCondition(new CastleDoomCondition());
    }
}

class CastleDoomCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        return source instanceof SpellAbility
            && !source.isActivated()
            && sourceObject != null
            && sourceObject.isArtifact(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
