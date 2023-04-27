
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki (Ursapine), LevelX2 (Eight-and-a-Half Tails), cbt
 */
public final class DevotedCaretaker extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("instant spells and sorcery spells");
    
    static{
        filter.add(Predicates.or(CardType.SORCERY.getPredicate(), CardType.INSTANT.getPredicate()));
    }

    public DevotedCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W}, {tap}: Target permanent you control gains protection from instant spells and from sorcery spells until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetControlledPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private DevotedCaretaker(final DevotedCaretaker card) {
        super(card);
    }

    @Override
    public DevotedCaretaker copy() {
        return new DevotedCaretaker(this);
    }
}
