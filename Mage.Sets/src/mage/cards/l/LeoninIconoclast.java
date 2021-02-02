
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class LeoninIconoclast extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchantment creature an opponent controls");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public LeoninIconoclast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Heroic — Whenever you cast a spell that targets Leonin Iconoclast, destroy target enchantment creature an opponent controls.
        Ability ability = new HeroicAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LeoninIconoclast(final LeoninIconoclast card) {
        super(card);
    }

    @Override
    public LeoninIconoclast copy() {
        return new LeoninIconoclast(this);
    }
}
