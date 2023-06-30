package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoothaMercurialArtist extends CardImpl {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RoothaMercurialArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {2}, Return Rootha, Mercurial Artist to its owner's hand: Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetSpellEffect(), new GenericManaCost(2));
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private RoothaMercurialArtist(final RoothaMercurialArtist card) {
        super(card);
    }

    @Override
    public RoothaMercurialArtist copy() {
        return new RoothaMercurialArtist(this);
    }
}
