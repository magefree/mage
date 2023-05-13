package mage.cards.h;

import java.util.Collection;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.util.CardUtil;

/**
 * @author LevelX
 */
public final class HisokaMinamoSensei extends CardImpl {

    public HisokaMinamoSensei(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {2}{U}, Discard a card: Counter target spell if it has the same converted mana cost as the discarded card.
        Ability ability = new SimpleActivatedAbility(new HisokaMinamoSenseiCounterEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private HisokaMinamoSensei(final HisokaMinamoSensei card) {
        super(card);
    }

    @Override
    public HisokaMinamoSensei copy() {
        return new HisokaMinamoSensei(this);
    }
}

class HisokaMinamoSenseiCounterEffect extends OneShotEffect {
    HisokaMinamoSenseiCounterEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if it has the same mana value as the discarded card";
    }

    HisokaMinamoSenseiCounterEffect(final HisokaMinamoSenseiCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell == null) {
            return false;
        }
        if (CardUtil.castStream(source.getCosts().stream(), DiscardTargetCost.class)
                .map(DiscardTargetCost::getCards)
                .flatMap(Collection::stream)
                .anyMatch(card -> card.getManaValue() == spell.getManaValue())) {
            return game.getStack().counter(targetPointer.getFirst(game, source), source, game);
        }
        return false;
    }

    @Override
    public HisokaMinamoSenseiCounterEffect copy() {
        return new HisokaMinamoSenseiCounterEffect(this);
    }
}
