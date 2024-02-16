package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DeclarationOfNaught extends CardImpl {

    static final private FilterSpell filter = new FilterSpell("spell with the chosen name");

    public DeclarationOfNaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // As Declaration of Naught enters the battlefield, name a card.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL)));

        // {U}: Counter target spell with the chosen name.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.setTargetAdjuster(DeclarationOfNaughtAdjuster.instance);
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private DeclarationOfNaught(final DeclarationOfNaught card) {
        super(card);
    }

    @Override
    public DeclarationOfNaught copy() {
        return new DeclarationOfNaught(this);
    }
}

enum DeclarationOfNaughtAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        String chosenName = (String) game.getState().getValue(ability.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (chosenName == null) {
            return;
        }
        FilterSpell filterSpell = new FilterSpell("spell named " + chosenName);
        filterSpell.add(new NamePredicate(chosenName));
        TargetSpell target = new TargetSpell(1, filterSpell);
        ability.addTarget(target);
    }
}
