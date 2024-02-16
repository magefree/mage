
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LoneFox
 */
public final class KithkinMourncaller extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("an attacking Kithkin or Elf");

    static {
        filter.add(Predicates.or(SubType.KITHKIN.getPredicate(), SubType.ELF.getPredicate()));
    }

    public KithkinMourncaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an attacking Kithkin or Elf is put into your graveyard from the battlefield, you may draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                true, filter, false, true
        ));
    }

    private KithkinMourncaller(final KithkinMourncaller card) {
        super(card);
    }

    @Override
    public KithkinMourncaller copy() {
        return new KithkinMourncaller(this);
    }
}
