
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class NivixGuildmage extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell you control");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }
    
    public NivixGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{U}{R}: Draw a card, then discard a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), new ManaCostsImpl("{1}{U}{R}")));
        
        // {2}{U}{R}: Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CopyTargetSpellEffect(), new ManaCostsImpl("{2}{U}{R}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private NivixGuildmage(final NivixGuildmage card) {
        super(card);
    }

    @Override
    public NivixGuildmage copy() {
        return new NivixGuildmage(this);
    }
}
