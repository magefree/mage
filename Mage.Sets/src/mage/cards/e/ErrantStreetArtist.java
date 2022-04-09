package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ErrantStreetArtist extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell you control that wasn't cast");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(ErrantStreetArtistPredicate.instance);
    }

    public ErrantStreetArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}{U}, {T}: Copy target spell you control that wasn't cast. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetSpellEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private ErrantStreetArtist(final ErrantStreetArtist card) {
        super(card);
    }

    @Override
    public ErrantStreetArtist copy() {
        return new ErrantStreetArtist(this);
    }
}

enum ErrantStreetArtistPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input.isCopy();
    }
}