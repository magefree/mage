
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GiltLeafWinnower extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Elf creature whose power and toughness aren't equal");

    static {
        filter.add(Predicates.not(SubType.ELF.getPredicate()));
        filter.add(new PowerToughnessNotEqualPredicate());
    }

    public GiltLeafWinnower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Gilt-Leaf Winnower enters the battlefield, you may destroy target non-Elf creature whose power and toughness aren't equal.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private GiltLeafWinnower(final GiltLeafWinnower card) {
        super(card);
    }

    @Override
    public GiltLeafWinnower copy() {
        return new GiltLeafWinnower(this);
    }
}

class PowerToughnessNotEqualPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getPower().getValue() != input.getToughness().getValue();
    }

    @Override
    public String toString() {
        return "power and toughness aren't equal";
    }
}
